import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './expenses.reducer';
import { IExpenses } from 'app/shared/model/expenses.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExpensesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ExpensesDetail extends React.Component<IExpensesDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { expensesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.expenses.detail.title">Expenses</Translate> [<b>{expensesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="expenseType">
                <Translate contentKey="accounTalkApp.expenses.expenseType">Expense Type</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.expenseType}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="accounTalkApp.expenses.description">Description</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.description}</dd>
            <dt>
              <span id="value">
                <Translate contentKey="accounTalkApp.expenses.value">Value</Translate>
              </span>
            </dt>
            <dd>{expensesEntity.value}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.expenses.employmentDetails">Employment Details</Translate>
            </dt>
            <dd>{expensesEntity.employmentDetails ? expensesEntity.employmentDetails.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/expenses" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/expenses/${expensesEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ expenses }: IRootState) => ({
  expensesEntity: expenses.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExpensesDetail);
