import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { getEntities as getTaxReturns } from 'app/entities/tax-return/tax-return.reducer';
import { IEmployment } from 'app/shared/model/employment.model';
import { getEntities as getEmployments } from 'app/entities/employment/employment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './employment-details.reducer';
import { IEmploymentDetails } from 'app/shared/model/employment-details.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmploymentDetailsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmploymentDetailsUpdateState {
  isNew: boolean;
  taxReturnId: string;
  employmentId: string;
}

export class EmploymentDetailsUpdate extends React.Component<IEmploymentDetailsUpdateProps, IEmploymentDetailsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      taxReturnId: '0',
      employmentId: '0',
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
    this.props.getEmployments();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { employmentDetailsEntity } = this.props;
      const entity = {
        ...employmentDetailsEntity,
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
    this.props.history.push('/entity/employment-details');
  };

  render() {
    const { employmentDetailsEntity, taxReturns, employments, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.employmentDetails.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.employmentDetails.home.createOrEditLabel">Create or edit a EmploymentDetails</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : employmentDetailsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="employment-details-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="employment-details-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="employment-details-taxReturn">
                    <Translate contentKey="accounTalkApp.employmentDetails.taxReturn">Tax Return</Translate>
                  </Label>
                  <AvInput id="employment-details-taxReturn" type="select" className="form-control" name="taxReturn.id">
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
                  <Label for="employment-details-employment">
                    <Translate contentKey="accounTalkApp.employmentDetails.employment">Employment</Translate>
                  </Label>
                  <AvInput id="employment-details-employment" type="select" className="form-control" name="employment.id">
                    <option value="" key="0" />
                    {employments
                      ? employments.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/employment-details" replace color="info">
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
  employments: storeState.employment.entities,
  employmentDetailsEntity: storeState.employmentDetails.entity,
  loading: storeState.employmentDetails.loading,
  updating: storeState.employmentDetails.updating,
  updateSuccess: storeState.employmentDetails.updateSuccess
});

const mapDispatchToProps = {
  getTaxReturns,
  getEmployments,
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
)(EmploymentDetailsUpdate);
