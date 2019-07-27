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
import { getEntity, updateEntity, createEntity, reset } from './eis-investments.reducer';
import { IEisInvestments } from 'app/shared/model/eis-investments.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEisInvestmentsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEisInvestmentsUpdateState {
  isNew: boolean;
  taxReturnId: string;
}

export class EisInvestmentsUpdate extends React.Component<IEisInvestmentsUpdateProps, IEisInvestmentsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      taxReturnId: '0',
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
  }

  saveEntity = (event, errors, values) => {
    values.dateInvested = convertDateTimeToServer(values.dateInvested);

    if (errors.length === 0) {
      const { eisInvestmentsEntity } = this.props;
      const entity = {
        ...eisInvestmentsEntity,
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
    this.props.history.push('/entity/eis-investments');
  };

  render() {
    const { eisInvestmentsEntity, taxReturns, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.eisInvestments.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.eisInvestments.home.createOrEditLabel">Create or edit a EisInvestments</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : eisInvestmentsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="eis-investments-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="eis-investments-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="investmentSchemeLabel" for="eis-investments-investmentScheme">
                    <Translate contentKey="accounTalkApp.eisInvestments.investmentScheme">Investment Scheme</Translate>
                  </Label>
                  <AvInput
                    id="eis-investments-investmentScheme"
                    type="select"
                    className="form-control"
                    name="investmentScheme"
                    value={(!isNew && eisInvestmentsEntity.investmentScheme) || 'EIS'}
                  >
                    <option value="EIS">{translate('accounTalkApp.InvestmentScheme.EIS')}</option>
                    <option value="SEIS">{translate('accounTalkApp.InvestmentScheme.SEIS')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="dateInvestedLabel" for="eis-investments-dateInvested">
                    <Translate contentKey="accounTalkApp.eisInvestments.dateInvested">Date Invested</Translate>
                  </Label>
                  <AvInput
                    id="eis-investments-dateInvested"
                    type="datetime-local"
                    className="form-control"
                    name="dateInvested"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.eisInvestmentsEntity.dateInvested)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="amountPaidLabel" for="eis-investments-amountPaid">
                    <Translate contentKey="accounTalkApp.eisInvestments.amountPaid">Amount Paid</Translate>
                  </Label>
                  <AvField id="eis-investments-amountPaid" type="string" className="form-control" name="amountPaid" />
                </AvGroup>
                <AvGroup>
                  <Label for="eis-investments-taxReturn">
                    <Translate contentKey="accounTalkApp.eisInvestments.taxReturn">Tax Return</Translate>
                  </Label>
                  <AvInput id="eis-investments-taxReturn" type="select" className="form-control" name="taxReturn.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/eis-investments" replace color="info">
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
  eisInvestmentsEntity: storeState.eisInvestments.entity,
  loading: storeState.eisInvestments.loading,
  updating: storeState.eisInvestments.updating,
  updateSuccess: storeState.eisInvestments.updateSuccess
});

const mapDispatchToProps = {
  getTaxReturns,
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
)(EisInvestmentsUpdate);
