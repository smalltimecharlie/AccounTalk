import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { getEntities as getTaxReturns } from 'app/entities/tax-return/tax-return.reducer';
import { IPensionProvider } from 'app/shared/model/pension-provider.model';
import { getEntities as getPensionProviders } from 'app/entities/pension-provider/pension-provider.reducer';
import { getEntity, updateEntity, createEntity, reset } from './per-pension-contributions.reducer';
import { IPerPensionContributions } from 'app/shared/model/per-pension-contributions.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPerPensionContributionsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPerPensionContributionsUpdateState {
  isNew: boolean;
  taxReturnId: string;
  pensionProviderId: string;
}

export class PerPensionContributionsUpdate extends React.Component<
  IPerPensionContributionsUpdateProps,
  IPerPensionContributionsUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      taxReturnId: '0',
      pensionProviderId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getTaxReturns();
    this.props.getPensionProviders();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { perPensionContributionsEntity } = this.props;
      const entity = {
        ...perPensionContributionsEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/per-pension-contributions');
  };

  render() {
    const { perPensionContributionsEntity, taxReturns, pensionProviders, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.perPensionContributions.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.perPensionContributions.home.createOrEditLabel">
                Create or edit a PerPensionContributions
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : perPensionContributionsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="per-pension-contributions-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="per-pension-contributions-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="netAmountPaidLabel" for="per-pension-contributions-netAmountPaid">
                    <Translate contentKey="accounTalkApp.perPensionContributions.netAmountPaid">Net Amount Paid</Translate>
                  </Label>
                  <AvField id="per-pension-contributions-netAmountPaid" type="string" className="form-control" name="netAmountPaid" />
                </AvGroup>
                <AvGroup>
                  <Label for="per-pension-contributions-taxReturn">
                    <Translate contentKey="accounTalkApp.perPensionContributions.taxReturn">Tax Return</Translate>
                  </Label>
                  <AvInput id="per-pension-contributions-taxReturn" type="select" className="form-control" name="taxReturn.id">
                    <option value="" key="0" />
                    {taxReturns
                      ? taxReturns.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="per-pension-contributions-pensionProvider">
                    <Translate contentKey="accounTalkApp.perPensionContributions.pensionProvider">Pension Provider</Translate>
                  </Label>
                  <AvInput id="per-pension-contributions-pensionProvider" type="select" className="form-control" name="pensionProvider.id">
                    <option value="" key="0" />
                    {pensionProviders
                      ? pensionProviders.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/per-pension-contributions" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  taxReturns: storeState.taxReturn.entities,
  pensionProviders: storeState.pensionProvider.entities,
  perPensionContributionsEntity: storeState.perPensionContributions.entity,
  loading: storeState.perPensionContributions.loading,
  updating: storeState.perPensionContributions.updating,
  updateSuccess: storeState.perPensionContributions.updateSuccess
});

const mapDispatchToProps = {
  getTaxReturns,
  getPensionProviders,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PerPensionContributionsUpdate);
